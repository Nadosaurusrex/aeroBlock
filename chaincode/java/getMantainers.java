package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftMaintenanceContract implements ContractInterface {

    @Transaction
    public List<String> getMaintenanceTechnicians(Context ctx, String aircraftId) {
        // Retrieve all maintenance records for the aircraft
        QueryResultsIterator<KeyValue> results = ctx.getStub().getStateByPartialCompositeKey("maintenance~aircraft", new String[] { aircraftId });

        // Create a list to store the maintenance technicians
        List<String> technicians = new ArrayList<>();

        // Iterate through the results and add each maintenance technician to the list
        while (results.iterator().hasNext()) {
            KeyValue kv = results.iterator().next();
            Maintenance maintenance = Maintenance.fromJSONString(new String(kv.getValue(), UTF_8));
            technicians.add(maintenance.getTechnician());
        }

        return technicians;
    }

}

