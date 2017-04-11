#!/bin/bash
java -Dserver.port=809$1 -Dbank.id=BANK$1 -Djms.queue.in=queue.bank.BANK$1.in -jar target/bank-service-0.0.1-SNAPSHOT.jar
