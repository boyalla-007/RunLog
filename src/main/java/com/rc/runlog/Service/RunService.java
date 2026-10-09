package com.rc.runlog.Service;

import com.rc.runlog.Dto.RunStatsResponse;
import com.rc.runlog.Repository.UserRepository;
import com.rc.runlog.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.rc.runlog.Dto.RunRequest;
import com.rc.runlog.Dto.RunResponse;
import com.rc.runlog.Repository.RunRepository;
import com.rc.runlog.exception.RunNotFoundException;
import com.rc.runlog.model.Run;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDate;
import java.util.List;
import com.rc.runlog.Dto.RunStatsResponse;

@Service
public class RunService {
    private final RunRepository runRepository;
    private final UserRepository userRepository;
    public RunService(RunRepository runRepo,UserRepository userRepo){
        this.runRepository = runRepo;
        this.userRepository = userRepo;
    }
    private User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User Not Found"));
    }
    public RunResponse saveRun(RunRequest request){
        Run run = new Run();
        User user = getCurrentUser();
        run.setUserId(user.getId());

        run.setDate(request.getDate());
        run.setDistance(request.getDistance());
        run.setDuration(request.getDuration());
        run.setNotes(request.getNotes());

        double pace = request.getDuration()/request.getDistance();
        run.setPace(pace);

        Run savedRun = runRepository.save(run);
        return convertToResponse(savedRun);
    }
    public RunResponse getRunById(ObjectId id){
        User user = getCurrentUser();
        Run run = runRepository.findByIdAndUserId(id,user.getId())
                .orElseThrow(()->
                        new RunNotFoundException("Run not found"));
        return convertToResponse(run);
    }

    public RunResponse updateRun(
            ObjectId id,RunRequest request
    ){
        User user = getCurrentUser();
       Run run = runRepository.findByIdAndUserId(id,user.getId())
               .orElseThrow(()->
        new RunNotFoundException("Run not found"));
            run.setDate(request.getDate());
            run.setDistance(request.getDistance());
            run.setDuration(request.getDuration());
            run.setNotes(request.getNotes());
            double pace = request.getDuration() / request.getDistance();
            run.setPace(pace);

            Run updatedRun = runRepository.save(run);

            return convertToResponse(updatedRun);
    }
    public void deleteRun(ObjectId id){
        User user = getCurrentUser();
        Run run = runRepository.findByIdAndUserId(id,user.getId())
                .orElseThrow(()->
                        new RunNotFoundException("Run Not Found"));
        runRepository.delete(run);
    }
    private RunResponse convertToResponse(Run run){
        return new RunResponse(
                run.getId().toString(),
                run.getDate(),
                run.getDistance(),
                run.getDuration(),
                run.getPace(),
                run.getNotes()
        );
    }
    public Page<RunResponse> getRuns(
            LocalDate from,
            LocalDate to,
            int page,
            int size
    ){
        User user = getCurrentUser();

        Pageable pageable = PageRequest.of(page,size);
        if(from != null && to != null){
            return runRepository
                    .findByUserIdAndDateBetween(user.getId(), from, to,pageable)
                    .map(this::convertToResponse);
        }
    else if(from!=null){
            return runRepository
                    .findByUserIdAndDateGreaterThanEqualOrderByDateDesc(user.getId(),from,pageable)
                    .map(this::convertToResponse);
        }
        else if(to!=null){
            return runRepository
                    .findByUserIdAndDateLessThanEqualOrderByDateDesc(user.getId(),to,pageable)
                    .map(this::convertToResponse);
        }
        return runRepository
                .findByUserIdOrderByDateDesc(user.getId(),pageable)
                .map(this::convertToResponse);
    }

    public RunStatsResponse getRunStats(){
        User user = getCurrentUser();

        List<Run> runs = runRepository.findByUserId(user.getId());

        long totalRuns = runs.size();
        double totalDistance = runs.stream()
                .mapToDouble(Run::getDistance)
                .sum();
        double averagePace = totalRuns==0?0:
                Math.round(
                runs.stream()
                .mapToDouble(Run::getPace)
                .average()
                .orElse(0)*100)/100;

        double averageDistance = totalRuns ==0?0:
                Math.round(
                runs.stream()
                .mapToDouble(Run::getPace)
                .average()
                .orElse(0)*100)/100;

        return new RunStatsResponse(
                totalRuns,
                totalDistance,
                averageDistance,
                averagePace
        );
    }
    public RunResponse getLongestRun(){
        User user = getCurrentUser();
        Run run = runRepository
                .findTopByUserIdOrderByDistanceDesc(user.getId())
                .orElseThrow(()->
                        new RunNotFoundException("No runs found"));
        return convertToResponse(run);
    }
}
