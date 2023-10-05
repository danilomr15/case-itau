FROM rabbitmq:3-management

ENV RABBITMQ_USER danilomr
ENV RABBITMQ_PASSWORD pass123
ENV RABBITMQ_PID_FILE /var/lib/rabbitmq/mnesia/rabbitmq

ADD init.sh /init.sh
RUN chmod +x /init.sh
EXPOSE 5672

CMD ["/init.sh"]