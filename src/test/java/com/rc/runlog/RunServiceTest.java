package com.rc.runlog;

import com.rc.runlog.Dto.RunRequest;
import com.rc.runlog.Dto.RunResponse;
import com.rc.runlog.Repository.RunRepository;
import com.rc.runlog.Repository.UserRepository;
import com.rc.runlog.Service.RunService;
import com.rc.runlog.exception.RunNotFoundException;
import com.rc.runlog.model.Run;
import com.rc.runlog.model.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.junit.jupiter.api.BeforeEach;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RunServiceTest {

    @Mock
    private RunRepository runRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private RunService runService;

    @BeforeEach
    void setUp(){
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        "rahul",
                        null
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Test
    void getRunById_showReturnRun_whenRunExists(){

        ObjectId userId = new ObjectId();
        ObjectId runId = new ObjectId();

        User user = new User();
        user.setId(userId);

        Run run = new Run();
        run.setId(runId);
        run.setUserId(userId);
        run.setDistance(5.0);
        run.setDuration(30);
        run.setPace(6.0);

        when(userRepository.findByUsername("rahul"))
                .thenReturn(Optional.of(user));

        when(runRepository.findByIdAndUserId(runId,userId))
                .thenReturn(Optional.of(run));

        RunResponse response = runService.getRunById(runId);

        assertEquals(runId.toString(),response.getId());
        assertEquals(5.0,response.getDistance());
        assertEquals(30,response.getDuration());
        assertEquals(6.0,response.getPace());
    }
    @AfterEach
    void tearDown(){
        SecurityContextHolder.clearContext();
    }
    @Test
    void getRunById_shouldThrowException_whenRunDoesNotExist(){

        ObjectId userId = new ObjectId();
        ObjectId runId = new ObjectId();

        User user = new User();
        user.setId(userId);

        when(userRepository.findByUsername("rahul"))
                .thenReturn(Optional.of(user));
        when(runRepository.findByIdAndUserId(runId,userId))
                .thenReturn(Optional.empty());
        assertThrows(
                RunNotFoundException.class,
                ()->runService.getRunById(runId)
        );
    }
    @Test
    void saveRun_shouldSaveRunAndReturnResponse(){

        ObjectId userId = new ObjectId();

        User user= new User();
        user.setId(userId);
        when(userRepository.findByUsername("rahul"))
                .thenReturn(Optional.of(user));

        RunRequest request = new RunRequest();
        request.setDistance(5.0);
        request.setDuration(30);

        when(runRepository.save(any(Run.class)))
                .thenAnswer(invocation -> {
                    Run run = invocation.getArgument(0);
                    run.setId(new ObjectId());
                    return run;
                });
        RunResponse response = runService.saveRun(request);

        assertEquals(5.0,request.getDistance());
        assertEquals(30,response.getDuration());
        assertEquals(6.0,response.getPace());
    }
}
