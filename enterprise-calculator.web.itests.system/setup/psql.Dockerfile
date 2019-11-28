FROM postgres:latest
ENV POSTGRES_PASSWORD zabravenaparola 
ENV POSTGRES_DB history 
EXPOSE 5432
COPY init.sql /docker-entrypoint-initdb.d/
RUN  echo  local all all trust >> /var/lib/postgresql/data/postgresql.conf
RUN  echo  host all all peer >> /var/lib/postgresql/data/postgresql.conf
RUN  echo  host all all 127.0.0.1/32 trust >> /var/lib/postgresql/data/postgresql.conf