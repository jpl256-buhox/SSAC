package mx.com.cfe.SSAC.api.datasyncapi.Controllers;

import mx.com.cfe.SSAC.api.datasyncapi.Services.DataSyncService;
import mx.com.cfe.SSAC.api.datasyncapi.Services.DataSyncServiceDeprecated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/V1.0")
public class DataSyncController {

    private static final Logger logger = LoggerFactory.getLogger(DataSyncController.class);
    @Autowired
    DataSyncService dataSyncService;

    @GetMapping
    public void extraerinformacion(String variable) {
        dataSyncService.extraerInformacion();

    }
}
