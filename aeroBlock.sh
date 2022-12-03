#!/bin/bash
set -e

green='\033[0;32m'
cd ../test-network
./network.sh down

./network.sh up createChannel
#./network.sh deployCC -ccn basic -ccp ../dApp2_project/chaincode/javascript -ccl javascript
export PATH=${PWD}/../bin:$PATH

export FABRIC_CFG_PATH=$PWD/../config/


peer lifecycle chaincode package basic.tar.gz --path ../aeroBlock/chaincode/javascript --lang node --label basic_1.0



export CORE_PEER_TLS_ENABLED=true
export CORE_PEER_LOCALMSPID="Org1MSP"
export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
export CORE_PEER_ADDRESS=localhost:7051

peer lifecycle chaincode install basic.tar.gz


export CORE_PEER_LOCALMSPID="Org2MSP"
export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt
export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp
export CORE_PEER_ADDRESS=localhost:9051

peer lifecycle chaincode install basic.tar.gz

PACKAGE_ID=$(peer lifecycle chaincode calculatepackageid basic.tar.gz)

#echo "${OUTPUT#* * * *ID: }" | sed 's/Package ID: //g' | sed 's/, Label: basic_1.0//g' | sed 's/Installed chaincodes on peer: //' | xargs
echo "${OUTPUT#* * * *ID: }"| cut -f1 -d","
export CC_PACKAGE_ID=$(peer lifecycle chaincode calculatepackageid basic.tar.gz)

peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name basic --version 1.0 --package-id $CC_PACKAGE_ID --sequence 1 --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem"
export CORE_PEER_LOCALMSPID="Org1MSP"
export CORE_PEER_MSPCONFIGPATH=${PWD}/organizations/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp
export CORE_PEER_TLS_ROOTCERT_FILE=${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt
export CORE_PEER_ADDRESS=localhost:7051

peer lifecycle chaincode approveformyorg -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name basic --version 1.0 --package-id $CC_PACKAGE_ID --sequence 1 --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem"

peer lifecycle chaincode checkcommitreadiness --channelID mychannel --name basic --version 1.0 --sequence 1 --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem" --output json 

peer lifecycle chaincode commit -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --channelID mychannel --name basic --version 1.0 --sequence 1 --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem" --peerAddresses localhost:7051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt" --peerAddresses localhost:9051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt" 

peer lifecycle chaincode querycommitted --channelID mychannel --name basic --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem" 

export CC_PACKAGE_ID=$(peer lifecycle chaincode calculatepackageid basic.tar.gz)

#peer chaincode invoke -o localhost:7050 --ordererTLSHostnameOverride orderer.example.com --tls --cafile "${PWD}/organizations/ordererOrganizations/example.com/orderers/orderer.example.com/msp/tlscacerts/tlsca.example.com-cert.pem" -C mychannel -n basic --peerAddresses localhost:7051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/tls/ca.crt" --peerAddresses localhost:9051 --tlsRootCertFiles "${PWD}/organizations/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/tls/ca.crt" -c '{"Args":["registerUser","{\"username\":\"testui\",\"password\":\"test123\",\"type\":\"administrator\",\"company\":\"alitalia\"}"]}'


# + peer lifecycle chaincode queryinstalled --output json
#+ jq -r 'try (.installed_chaincodes[].package_id)'
#+ grep '^basic_1.0:dc37b27f2fdd2bedeb8636d23788d251888b3d2999afd6dc7c02228e1abd74b1$'

# per sistemare la cosa del lpackageid prova sto codice, per capire cosa fa runna deployeCC
# cerca di capire anche che problema da deployCC e perché non va

# ti eri fermato all npm install nella cartella chaincode lib che ti faceva le funzioni js per runnare terminal
# hai trovato errore grpc -≥ deriva da fabric-network (mettilo sull'ultima versione nel package.json)
 

#--signature-policy "OR ('Org1MSP.peer','Org2MSP.peer')"
