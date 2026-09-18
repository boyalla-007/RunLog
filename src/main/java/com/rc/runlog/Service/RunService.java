package com.rc.runlog.Service;

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

import java.util.List;
import java.util.Optional;

@Service
public class RunService {
    private final RunRepository runRepository;
    private final UserRepository userRepository;
    public RunService(RunRepository runRepo,UserRepository userRepo){
        this.runRepository = runRepo;
        this.userRepository = userRepo;
    }

    public RunResponse saveRun(RunRequest request){
        Run run = new Run();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                        .orElseThrow(()->new RunNotFoundException("User Not Found!"));
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
    public List<RunResponse> getAllRuns(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new RunNotFoundException("User Not Found"));
        return runRepository.findByUserId(user.getId())
                .stream()
                .map(this::convertToResponse)
                .toList();
    }
    public RunResponse getRunById(ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(()-> new RuntimeException("User Not Found"));
        Run run = runRepository.findById(id)
                .orElseThrow(()->
                        new RunNotFoundException("Run not found"));
        if(!run.getUserId().equals(user.getId())){
            throw  new RunNotFoundException("Run Not Found");
        }
        return convertToResponse(run);
    }

    public RunResponse updateRun(
            ObjectId id,RunRequest request
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User Not Found"));
       Run run = runRepository.findById(id)
               .orElseThrow(()->
        new RunNotFoundException("Run not found"));
       if(!run.getUserId().equals(user.getId())){
           throw new RunNotFoundException("Run Not Found");
       }
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username)
                        .orElseThrow(()->new RuntimeException("User Not Found"));
        Run run = runRepository.findById(id)
                .orElseThrow(()->
                        new RunNotFoundException("Run Not Found"));
        if(!user.getId().equals(run.getUserId())){
            throw new RunNotFoundException("Run Not Found");
        }
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
}
