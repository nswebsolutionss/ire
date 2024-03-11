DROP TABLE IF EXISTS user_information CASCADE;
DROP TABLE IF EXISTS account_information CASCADE;
DROP VIEW IF EXISTS user_information_view ;
DROP VIEW IF EXISTS users_view ;
DROP VIEW IF EXISTS account_information ;

DROP TABLE IF EXISTS users;
DROP FUNCTION IF EXISTS create_user;
DROP TYPE IF EXISTS address_type;

CREATE TYPE address_type AS (
    house_number VARCHAR(255),
    flat_number VARCHAR(255),
    first_line VARCHAR(255),
    county VARCHAR(255),
    country VARCHAR(255),
    postcode VARCHAR(255)
);

CREATE TABLE users (
    user_id BIGINT GENERATED ALWAYS AS IDENTITY,
    email_address VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_date BIGINT NOT NULL,
    last_login BIGINT NULL,
    failed_login_attempts INT NOT NULL,
    PRIMARY KEY(user_id)
);

CREATE TABLE user_information (
    user_information_id BIGINT GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    address address_type NOT NULL,
    phone_number VARCHAR(255) NOT NULL,
    last_updated BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY(user_information_id)
);

CREATE TABLE account_information (
    account_information_id BIGINT GENERATED ALWAYS AS IDENTITY,
    subscription_status VARCHAR(255) NOT NULL,
    account_type VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    PRIMARY KEY(account_information_id)
);

CREATE OR REPLACE VIEW user_information_view
    AS SELECT user_information_id, first_name, last_name, address, phone_number, last_updated FROM user_information WHERE (user_id=CAST(current_setting('my.user_id') AS BIGINT));

CREATE OR REPLACE VIEW account_information_view
    AS SELECT account_information_id, subscription_status, account_type FROM account_information WHERE (user_id=CAST(current_setting('my.user_id') AS BIGINT));

CREATE OR REPLACE VIEW users_view
    AS SELECT user_id, email_address, password_hash, created_date, last_login, failed_login_attempts FROM users WHERE (user_id=CAST(current_setting('my.user_id') AS BIGINT));

CREATE OR REPLACE FUNCTION create_user(
    _userId BIGINT
) RETURNS VOID  AS
$BODY$
DECLARE
BEGIN
        EXECUTE FORMAT('SET my.user_id=%L', _userId);
END;
$BODY$
LANGUAGE plpgsql STRICT VOLATILE SECURITY DEFINER
COST 100;


CREATE OR REPLACE FUNCTION pre_populate_user_id() RETURNS TRIGGER  AS
$BODY$
DECLARE
BEGIN
        NEW.user_id := CAST(current_setting('my.user_id') AS BIGINT);
        RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql STRICT VOLATILE SECURITY DEFINER
COST 100;

CREATE TRIGGER populate_user_id
BEFORE INSERT ON user_information FOR EACH ROW EXECUTE PROCEDURE  pre_populate_user_id();

CREATE TRIGGER populate_user_id
BEFORE INSERT ON account_information FOR EACH ROW EXECUTE PROCEDURE  pre_populate_user_id();


ALTER FUNCTION create_user(BIGINT) OWNER to superuser;


