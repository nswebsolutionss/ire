

create table if not exists
    organization (
        id text not null,
        constraint organization_pkey primary key (id)
    );

create table if not exists
  organization_information (
    id bigint generated always as identity,
    company_name text null,
    company_description text null,
    telephone_number text null,
    website_url text null,
    facebook_url text null,
    instagram_url text null,
    youtube_url text null,
    member_since bigint,
    last_updated bigint,
    organization_id text references organization(id) on delete cascade,
    constraint organization_information_pkey primary key (id)
  );

create table if not exists
property_details (
  id bigint generated always as identity,
  date_added bigint null,
  last_updated bigint null,
  property_type text null,
  beds int null,
  bathrooms int null,
  price text null,
  currency text null,
  organization_id text references organization(id) on delete cascade,
  constraint property_details_pkey primary key (id)
);

create table if not exists
    property_address (
    id bigint generated always as identity,
    property_details_id bigint references property_details(id) on delete cascade,
    building_identifier text null,
    street_name text null,
    city text null,
    county text null,
    postcode text null,
    country text null,
    organization_id text,
    constraint property_address_pkey primary key (id)
  );


