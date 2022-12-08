package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftMaintenanceContract implements ContractInterface {

    @Transaction
    public void performMaintenance(Context ctx, String aircraftId, String maintenanceType, String maintenanceDescription, String maintenanceDate) {
        // Create a new Maintenance instance
        Maintenance maintenance = new Maintenance();
        maintenance.setAircraftId(aircraftId);
        maintenance.setType(maintenanceType);
        maintenance.setDescription(maintenanceDescription);
        maintenance.setDate(maintenanceDate);

        // Save the Maintenance instance to the ledger
        ctx.getStub().putState(maintenance.getId(), maintenance.toJSONString().getBytes(UTF_8));
    }

}

