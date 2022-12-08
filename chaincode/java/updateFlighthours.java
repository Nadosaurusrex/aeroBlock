package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class FlightHoursContract implements ContractInterface {

    @Transaction
    public void updateFlightHours(Context ctx, String aircraftId, int flightHours) {
        // Get the current flight hours for the aircraft
        byte[] bytes = ctx.getStub().getState(aircraftId);
        int currentFlightHours = Integer.parseInt(new String(bytes, UTF_8));

        // Calculate the new flight hours
        int newFlightHours = currentFlightHours + flightHours;

        // Save the new flight hours to the ledger
        ctx.getStub().putState(aircraftId, Integer.toString(newFlightHours).getBytes(UTF_8));
    }

}

