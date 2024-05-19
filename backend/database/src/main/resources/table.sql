create table if not exists
  organization_information (
    id uuid not null default random_uuid(),
    company_name text null,
    company_description text null,
    telephone_number text null,
    website_url text null,
    facebook_url text null,
    instagram_url text null,
    youtube_url text null,
    member_since bigint,
    last_updated bigint,
    organization_id text not null,
    constraint organization_information_pkey primary key (id)
  );