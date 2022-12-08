package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftRegistrationContract implements ContractInterface {

    @Transaction
    public void registerAircraft(Context ctx, String aircraftId, String aircraftType, List<String> partNumbers) {
        // Create a new Aircraft instance
        Aircraft aircraft = new Aircraft();
        aircraft.settailNumber(aircrafttailNumber);
        aircraft.setCompany(aircraftCompany);
        aircraft.setImage(aircraftImage);
        aircraft.setParts(partNumbers);

        // Save the Aircraft instance to the ledger
        ctx.getStub().putState(aircraft.getId(), aircraft.toJSONString().getBytes(UTF_8));
    }

}

