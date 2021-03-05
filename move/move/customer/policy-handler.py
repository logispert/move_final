from flask import Flask
from redis import Redis, RedisError
from kafka import KafkaConsumer
import os
import socket

# To consume latest messages and auto-commit offsets
#                         bootstrap_servers=['my-kafka.kafka.svc.cluster.local:9092'])
consumer = KafkaConsumer('move24mall',
                         group_id='customer',
                         bootstrap_servers=['localhost:9092'])
for message in consumer:
    print ("%s:%d:%d: key=%s value=%s" % (message.topic, message.partition, message.offset, message.key, message.value.decode('utf-8')))


