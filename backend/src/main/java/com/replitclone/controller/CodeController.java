package com.ide.controller;

import com.ide.model.CodeInput;
import com.ide.service.CodeExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CodeController {

    @PostMapping("/execute")
    public ResponseEntity<Map<String, String>> executeCode(@RequestBody Map<String, String> body) {
        String code = body.get("code");
        String language = body.get("language");
        Map<String, String> output = CodeExecutor.runCode(code, language);
        return ResponseEntity.ok(output);
    }

}
