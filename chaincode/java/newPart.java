package org.example;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Transaction;

@Contract
public class AircraftPartContract implements ContractInterface {

    @Transaction
    public void createAircraftPart(Context ctx, String partNumber, String partDescription, String partSerialNumber, String partManufacturer) {
        AircraftPart part = new AircraftPart();
        part.setPartNumber(partNumber);
        part.setPartDescription(partDescription);
        part.setPartSerialNumber(partSerialNumber);
        part.setPartManufacturer(partManufacturer);

        ctx.getStub().putState(partNumber, part.toJSONString().getBytes(UTF_8));
    }

}

