# demo API Documentation

## Introduction

## GET /api/todos

`GET` /api/todos

### Description

Fetches all to-dos.

### Request Parameters

None

### Response

- Response Parameters. JSON array format description:

```json
[
  {
    "id": "integer //",
    "title": "string //",
    "completed": "boolean //"
  }
]
```


## POST /api/todos

`POST` /api/todos

### Description

Creates a new to-do. Expects a JSON body with "title" and optional "completed".

### Request Parameters

- Body Parameters. JSON object format description:

```json
{
  "id": "integer //",
  "title": "string //",
  "completed": "boolean //"
}
```

### Response

- Response Parameters. JSON object format description:

```json
{
  "id": "integer //",
  "title": "string //",
  "completed": "boolean //"
}
```


## DELETE /api/todos/

`DELETE` /api/todos/{id}

### Description

```
{
id
}
Deletes the to-do with the given ID.
```

### Request Parameters

- Path Parameters

| Field | Type | Required | Description |
| --- | --- | --- | --- |
| id | integer | Yes |  |

### Response

None


## PUT /api/todos/

`PUT` /api/todos/{id}

### Description

```
{
id
}
Update title and/or completed status of an existing to-do.
Client can send a JSON body with any subset of
{
"title": "...", "completed": true/false
}
.
```

### Request Parameters

- Path Parameters

| Field | Type | Required | Description |
| --- | --- | --- | --- |
| id | integer | Yes |  |

- Body Parameters. JSON object format description:

```json
{
  "id": "integer //",
  "title": "string //",
  "completed": "boolean //"
}
```

### Response

- Response Parameters. JSON object format description:

```json
{
  "id": "integer //",
  "title": "string //",
  "completed": "boolean //"
}
```


