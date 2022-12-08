package org.aeroblock;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftMaintenanceAssignmentContract implements ContractInterface {

    @Transaction
    public void assignMaintenanceTechnician(Context ctx, String aircraftId, String technicianId) {
        // Retrieve the aircraft from the ledger
        byte[] bytes = ctx.getStub().getState(aircraftId);
        Aircraft aircraft = Aircraft.fromJSONString(new String(bytes, UTF_8));

        // Assign the maintenance technician to the aircraft
        aircraft.setMaintenanceTechnician(technicianId);

        // Save the updated aircraft to the ledger
        ctx.getStub().putState(aircraftId, aircraft.toJSONString().getBytes(UTF_8));
    }

}

