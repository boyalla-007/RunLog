package com.rc.runlog.Controller;

import com.rc.runlog.Dto.RunRequest;
import com.rc.runlog.Dto.RunResponse;
import com.rc.runlog.Dto.RunStatsResponse;
import com.rc.runlog.Service.RunService;
import com.rc.runlog.model.Run;
import jakarta.validation.Valid;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    @GetMapping("/{id}")
    public RunResponse getRunById(
            @PathVariable("id") ObjectId id) {
        return runService.getRunById(id);
    }
    @GetMapping
    public Page<RunResponse> getRuns(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return runService.getRuns(from,to,page,size);
    }

    @PutMapping("/{Id}")
    public  RunResponse updateRun(@PathVariable ObjectId Id,@Valid @RequestBody RunRequest request){
        return runService.updateRun(Id,request);
    }
    @DeleteMapping("/{id}")
    public void deleteRun(@PathVariable("id") ObjectId id){
        runService.deleteRun(id);
    }
    @GetMapping("/stats")
    public RunStatsResponse getRunStats(){
        return runService.getRunStats();
    }
    @GetMapping("/longest")
    public RunResponse getLongestRun(){
        return runService.getLongestRun();
    }
}
