# Gyms

## Get all gyms

`GET` `/api/gyms`

### Response
#### 200
```Json
[
    {
        "id": "3f9bd5b3",
        "name": "Another Gym",
        "logo": "https://via.placeholder.com/700x500",
        "phone": "0712345678",
        "website": "https://website.com/",
        "images": [
            "https://via.placeholder.com/700x500"
        ],
        "openTime": 300,
        "closeTime": 1290,
        "available": false,
        "country": "Kenya",
        "city": "Nairobi",
        "cordsLat": -1.297262,
        "cordsLng": 36.800156
    }
]
```

## Get gym by id

`GET` `/api/gyms/{id}`

### Response
#### 200
```Json
{
    "id": "3f9bd5b3",
    "name": "Another Gym",
    "logo": "https://via.placeholder.com/700x500",
    "phone": "0712345678",
    "website": "https://website.com/",
    "images": [
        "https://via.placeholder.com/700x500"
    ],
    "openTime": 300,
    "closeTime": 1290,
    "available": false,
    "country": "Kenya",
    "city": "Nairobi",
    "cordsLat": -1.297262,
    "cordsLng": 36.800156
}
```
#### 404
```Json
{
    "message": "There is no gym with id 3f9bd5b3s"
}
```
## Get nearby gyms

`GET` `/api/gyms/nearby`

### Response
#### 200
```Json
[
    {
        "id": "3f9bd5b3",
        "name": "Another Gym",
        "logo": "https://via.placeholder.com/700x500",
        "phone": "0712345678",
        "website": "https://website.com/",
        "images": [
            "https://via.placeholder.com/700x500"
        ],
        "openTime": 300,
        "closeTime": 1290,
        "available": false,
        "country": "Kenya",
        "city": "Nairobi",
        "cordsLat": -1.297262,
        "cordsLng": 36.800156
    }
]
```

## Search for gym by name

`GET` `/api/gyms/search?name={name}`

### Response
#### 200
```Json
[
    {
        "id": "3f9bd5b3",
        "name": "Another Gym",
        "logo": "https://via.placeholder.com/700x500",
        "phone": "0712345678",
        "website": "https://website.com/",
        "images": [
            "https://via.placeholder.com/700x500"
        ],
        "openTime": 300,
        "closeTime": 1290,
        "available": false,
        "country": "Kenya",
        "city": "Nairobi",
        "cordsLat": -1.297262,
        "cordsLng": 36.800156
    }
]
```

## Create gym

`POST` `/api/gyms/create`

### Request
```Json
{
    "cordsLat": -1.297262,
    "cordsLng": 36.800157,
    "name": "Another Gym",
    "logo": "https://via.placeholder.com/700x500",
    "phone": "0712345678",
    "website": "https://website.com/",
    "openTime": 300,
    "closeTime": 1290,
    "country": "Kenya",
    "city": "Nairobi",
    "images": [
            "https://via.placeholder.com/700x500"
        ]
}
```

### Response
#### 201
```Json
{
    "id": "3f9bd5b3",
    "name": "Another Gym",
    "logo": "https://via.placeholder.com/700x500",
    "phone": "0712345678",
    "website": "https://website.com/",
    "images": [],
    "openTime": 300,
    "closeTime": 1290,
    "available": false,
    "country": "Kenya",
    "city": "Nairobi",
    "cordsLat": -1.297262,
    "cordsLng": 36.800156
}
```

## Update gym

`POST` `/api/gyms/update`

### Request
```Json
{
    "cordsLat": -1.297262,
    "cordsLng": 36.800157,
    "name": "Another Gym",
    "logo": "https://via.placeholder.com/700x500",
    "phone": "0712345678",
    "website": "https://website.com/",
    "openTime": 300,
    "closeTime": 1290,
    "country": "Kenya",
    "city": "Nairobi",
    "images": [
        "https://via.placeholder.com/700x500"
    ]
}
```

### Response
#### 200
```Json
{
    "cordsLat": -1.297262,
    "cordsLng": 36.800157,
    "name": "Another Gym",
    "logo": "https://via.placeholder.com/700x500",
    "phone": "0712345678",
    "website": "https://website.com/",
    "openTime": 300,
    "closeTime": 1290,
    "country": "Kenya",
    "city": "Nairobi",
    "images": [
        "https://via.placeholder.com/700x500"
    ]
}
```
