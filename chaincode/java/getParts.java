package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftPartContract implements ContractInterface {

    @Transaction
    public List<AircraftPart> getAircraftParts(Context ctx, String aircraftId) {
        // Retrieve the aircraft from the ledger
        byte[] bytes = ctx.getStub().getState(aircraftId);
        Aircraft aircraft = Aircraft.fromJSONString(new String(bytes, UTF_8));

        // Retrieve all the part numbers for the aircraft
        List<String> partNumbers = aircraft.getParts();

        // Create a list to store the aircraft parts
        List<AircraftPart> parts = new ArrayList<>();

        // Iterate through the part numbers and retrieve each part
        for (String partNumber : partNumbers) {
            bytes = ctx.getStub().getState(partNumber);
            parts.add(AircraftPart.fromJSONString(new String(bytes, UTF_8)));
        }

        return parts;
    }

}

