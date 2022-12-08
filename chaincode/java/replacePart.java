package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftPartReplacementContract implements ContractInterface {

    @Transaction
    public void replaceAircraftPart(Context ctx, String aircraftId, String oldPartNumber, String newPartNumber) {
        // Retrieve the aircraft from the ledger
        byte[] bytes = ctx.getStub().getState(aircraftId);
        Aircraft aircraft = Aircraft.fromJSONString(new String(bytes, UTF_8));

        // Replace the old part with the new part in the aircraft's parts list
        List<String> parts = aircraft.getParts();
        int index = parts.indexOf(oldPartNumber);
        parts.set(index, newPartNumber);
        aircraft.setParts(parts);

        // Save the updated aircraft to the ledger
        ctx.getStub().putState(aircraftId, aircraft.toJSONString().getBytes(UTF_8));
    }

}

