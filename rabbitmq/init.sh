#!/bin/bash

( rabbitmqctl wait --timeout 60 $RABBITMQ_PID_FILE ; \
rabbitmqctl add_user $RABBITMQ_USER $RABBITMQ_PASSWORD 2>/dev/null ; \
rabbitmqctl set_user_tags $RABBITMQ_USER administrator ; \
rabbitmqctl add_vhost spring_host; \
rabbitmqctl set_permissions -p spring_host $RABBITMQ_USER ".*" ".*" ".*"; \
echo "*** User '$RABBITMQ_USER' with password '$RABBITMQ_PASSWORD' completed. ***" ; \
echo "*** Log in the WebUI at port 15672 (example: http:/localhost:15672) ***" ; \

rabbitmqadmin declare exchange --vhost=spring_host name=add_restriction_exchange type=direct -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare queue --vhost=spring_host name=add_restriction_queue durable=true -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare binding --vhost=spring_host source=add_restriction_exchange destination_type=queue destination=add_restriction_queue routing_key=add_restriction_routing_key -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
echo "*** Queue add_restriction created ***"

rabbitmqadmin declare exchange --vhost=spring_host name=delete_restriction_exchange type=direct -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare queue --vhost=spring_host name=delete_restriction_queue durable=true -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare binding --vhost=spring_host source=delete_restriction_exchange destination_type=queue destination=delete_restriction_queue routing_key=delete_restriction_routing_key -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
echo "*** Queue delete_restriction created ***"

rabbitmqadmin declare exchange --vhost=spring_host name=charge_exchange type=direct -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare queue --vhost=spring_host name=charge_queue durable=true -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare binding --vhost=spring_host source=charge_exchange destination_type=queue destination=charge_queue routing_key=charge_routing_key -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
echo "*** Queue charge created ***"

rabbitmqadmin declare exchange --vhost=spring_host name=add_debit_exchange type=direct -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare queue --vhost=spring_host name=add_debit_queue durable=true -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
rabbitmqadmin declare binding --vhost=spring_host source=add_debit_exchange destination_type=queue destination=add_debit_queue routing_key=add_debit_routing_key -u $RABBITMQ_USER -p $RABBITMQ_PASSWORD ; \
echo "*** Queue add_debit created ***"

echo "*** Exchanges, Queues and Bindings created ***" ) &

 rabbitmq-server $@