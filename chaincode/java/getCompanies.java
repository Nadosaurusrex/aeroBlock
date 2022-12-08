package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftCompaniesContract implements ContractInterface {

    @Transaction
    public List<String> getAircraftCompanies(Context ctx, String aircraftId) {
        // Retrieve the aircraft from the ledger
        byte[] bytes = ctx.getStub().getState(aircraftId);
        Aircraft aircraft = Aircraft.fromJSONString(new String(bytes, UTF_8));

        // Retrieve all the part numbers for the aircraft
        List<String> partNumbers = aircraft.getParts();

        // Create a list to store the companies that manufacture the parts
        List<String> companies = new ArrayList<>();

        // Iterate through the part numbers and retrieve the manufacturer for each part
        for (String partNumber : partNumbers) {
            bytes = ctx.getStub().getState(partNumber);
            AircraftPart part = AircraftPart.fromJSONString(new String(bytes, UTF_8));
            companies.add(part.getPartManufacturer());
        }

        return companies;
    }

}

