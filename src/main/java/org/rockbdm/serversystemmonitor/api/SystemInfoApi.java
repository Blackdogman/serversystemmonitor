package org.rockbdm.serversystemmonitor.api;

import org.rockbdm.serversystemmonitor.tools.SystemInfoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class SystemInfoApi {
    private static SystemInfoTools systemInfoTools;

    @Autowired
    public SystemInfoApi(SystemInfoTools systemInfoTools) {
        SystemInfoApi.systemInfoTools = systemInfoTools;
    }

    @GetMapping("/MainSystemInfo")
    public Map<String, Double> getMainSystemInfo() throws IOException {
        return systemInfoTools.getSystemInfo();
    }
}
