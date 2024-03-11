DROP TABLE IF EXISTS organization_information CASCADE;
DROP VIEW IF EXISTS organization_information_view ;

CREATE TABLE organization_information (
    organization_id UUID NOT NULL,
    company_name VARCHAR(255),
    telephone_number VARCHAR(255),
    website_url VARCHAR(255),
    facebook_url VARCHAR(255),
    instagram_url VARCHAR(255),
    youtube_url VARCHAR(255),
    company_description TEXT,
    member_since BIGINT,
    last_updated BIGINT,
    PRIMARY KEY(organization_id)
);




