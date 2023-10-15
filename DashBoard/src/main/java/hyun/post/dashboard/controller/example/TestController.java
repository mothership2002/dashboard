package hyun.post.dashboard.controller.example;

import hyun.post.dashboard.model.dto.example.TestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "test 컨트롤러", description = "테스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/hello")
public class TestController {

    @GetMapping("/test")
    @Operation(operationId = "test", summary = "테스트", description = "테스트 입니다")
    public ResponseEntity<?> test(TestDto test) {
        return new ResponseEntity<>(test.getName(), HttpStatus.OK);
    }

}
