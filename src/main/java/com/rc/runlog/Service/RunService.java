package com.rc.runlog.Service;

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
    public RunService(RunRepository runRepo){
        this.runRepository = runRepo;
    }

    public RunResponse saveRun(RunRequest request){
        Run run = new Run();

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
        return runRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }
    public RunResponse getRunById(ObjectId id){
        Run run = runRepository.findById(id)
                .orElseThrow(()->
                        new RunNotFoundException("Run not found"));

        return convertToResponse(run);
    }

    public RunResponse updateRun(
            ObjectId id,RunRequest request
    ){
       Run run = runRepository.findById(id)
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
        runRepository.deleteById(id
        );
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
