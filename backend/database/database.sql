DROP TABLE IF EXISTS organization_information CASCADE;
DROP VIEW IF EXISTS organization_information_view ;


DROP TABLE IF EXISTS account_information CASCADE;
DROP VIEW IF EXISTS users_view ;
DROP VIEW IF EXISTS account_information ;


CREATE TABLE organization_information (
    organization_id UUID NOT NULL,
    company_name VARCHAR(255),
    telephone_number VARCHAR(255).
    website_url VARCHAR(255),
    facebook_url VARCHAR(255),
    instagram_url VARCHAR(255),
    youtube_url VARCHAR(255),
    company_description TEXT,
    member_since BIGINT,
    last_updated BIGINT,
    PRIMARY KEY(organization_id) 
)


CREATE OR REPLACE VIEW organization_information_view
    AS SELECT organization_id, 
    company_name, 
    telephone_number,
    website_url,
    facebook_url,
    instagram_url,
    youtube_url,
    company_description,
    member_since,
    last_updated
    FROM organization_information WHERE (user_id=CAST(current_setting('my.user_id') AS UUID));


CREATE OR REPLACE FUNCTION create_user(
    _userId UUID
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
        NEW.user_id := CAST(current_setting('my.user_id') AS UUID);
        RETURN NEW;
END;
$BODY$
LANGUAGE plpgsql STRICT VOLATILE SECURITY DEFINER
COST 100;

CREATE TRIGGER populate_user_id
BEFORE INSERT ON organization_information FOR EACH ROW EXECUTE PROCEDURE  pre_populate_user_id();


ALTER FUNCTION create_user(UUID) OWNER to superuser;


