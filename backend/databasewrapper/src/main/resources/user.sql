CREATE USER db_user WITH PASSWORD 'P4ssword.';
GRANT ALL PRIVILEGES ON DATABASE ire TO db_user;

create table if not exists
  organization_information (
    id uuid not null,
    company_name text null,
    company_description text null,
    telephone_number text null,
    website_url text null,
    facebook_url text null,
    instagram_url text null,
    youtube_url text null,
    member_since bigint,
    last_updated bigint,

    constraint organization_information_pkey primary key (id)
  );