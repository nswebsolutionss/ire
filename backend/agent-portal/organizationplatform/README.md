API DOCS
-
Get Response:

- Get response:

```json
{
  "statusCode": 200,
  "contentType": "JSON",
  "contentBody": {
    "id": "96194de8-32b6-424a-aef2-eae77c3b98ab",
    "companyName": "companyName",
    "companyDescription": "companyDescription",
    "telephoneNumber": "telephoneNumber",
    "websiteUrl": "websiteUrl",
    "facebookUrl": "facebookUrl",
    "instagramUrl": "instagramUrl",
    "youtubeUrl": "youtubeUrl",
    "memberSince": 1716411582464,
    "lastUpdated": 1716411582464
  },
  "headers": {}
}
```

- ID specific response:

```json
{
  "statusCode": 409,
  "contentType": "JSON",
  "contentBody": {
    "id": "cdb4158d-413c-431f-9072-3001d00cdcf6",
    "message": "Organization Information already exists"
  },
  "headers": {}
}

```

Get Request

- url: http://domain-name/api/organizationInformation/:id

Delete Request

- url: http://domain-name/api/organizationInformation/:id

Create Request

```json
{
  "payload": {
    "id": "96194de8-32b6-424a-aef2-eae77c3b98ab",
    "companyName": "companyName",
    "companyDescription": "companyDescription",
    "telephoneNumber": "telephoneNumber",
    "websiteUrl": "websiteUrl",
    "facebookUrl": "facebookUrl",
    "instagramUrl": "instagramUrl",
    "youtubeUrl": "youtubeUrl",
    "memberSince": 1716411582464,
    "lastUpdated": 1716411582464
  }
}
```

Update Request

```json
{
  "payload": {
    "id": "96194de8-32b6-424a-aef2-eae77c3b98ab",
    "companyName": "companyName",
    "companyDescription": "companyDescription",
    "telephoneNumber": "telephoneNumber",
    "websiteUrl": "websiteUrl",
    "facebookUrl": "facebookUrl",
    "instagramUrl": "instagramUrl",
    "youtubeUrl": "youtubeUrl",
    "memberSince": 1716411582464,
    "lastUpdated": 1716411582464
  }
}
```