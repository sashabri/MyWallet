create table if not exists wallet_info (
                                          id serial primary key,
                                          wallet_id  uuid not null unique,
                                          balance int
);