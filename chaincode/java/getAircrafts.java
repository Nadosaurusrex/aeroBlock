package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftContract implements ContractInterface {

    @Transaction
    public List<Aircraft> getAircrafts(Context ctx) {
        // Create a list to store the aircrafts
        List<Aircraft> aircrafts = new ArrayList<>();

        // Retrieve all the aircrafts in the ledger
        QueryResultsIterator<KeyValue> results = ctx.getStub().getStateByRange("", "");

        // Iterate through the results and add each aircraft to the list
        while (results.iterator().hasNext()) {
            KeyValue kv = results.iterator().next();
            aircrafts.add(Aircraft.fromJSONString(new String(kv.getValue(), UTF_8)));
        }

        return aircrafts;
    }

}

