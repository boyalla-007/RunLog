package com.rc.runlog.Controller;

import com.rc.runlog.Dto.RunRequest;
import com.rc.runlog.Dto.RunResponse;
import com.rc.runlog.Service.RunService;
import com.rc.runlog.model.Run;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/runs")
public class RunController {
    private RunService runService;
    public RunController(RunService runService){
        this.runService = runService;
    }
    @PostMapping
    public RunResponse createRun(@Valid  @RequestBody RunRequest request){
        return runService.saveRun(request);
    }
    @GetMapping
    public List<RunResponse> getAllRuns(){
        return runService.getAllRuns();
    }
    @GetMapping("/{id}")
    public RunResponse getRunById(
            @PathVariable("id") ObjectId id) {
        return runService.getRunById(id);
    }
    @PutMapping("/{Id}")
    public  RunResponse updateRun(@PathVariable ObjectId Id,@Valid @RequestBody RunRequest request){
        return runService.updateRun(Id,request);
    }
    @DeleteMapping("/{id}")
    public void deleteRun(@PathVariable ObjectId Id){
        runService.deleteRun(Id);
    }
}
