# Users

## Get all users

`GET` `/api/users`

### Response

```Json
[
    {
        "id": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
        "firstName": "fName",
        "lastName": "lName",
        "email": "fName@mail.com",
        "photo": "https://via.placeholder.com/500x500",
        "yearOfBirth": 1998,
        "gender": "M",
        "country": "Kenya",
        "weight": 65,
        "targetWeight": 60
    }
]
```

## Get user by id

`GET` `/api/users/{id}`

### Response
#### 200
```Json
{
    "id": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya",
    "weight": 65,
    "targetWeight": 60
}
```
#### 404
```Json
{
    "message": "There is no user with id oPlUSF8I0UOqUDsUhy80kMj59SV2s"
}
```
## Get user's preferred gym

`GET` `/api/users/{id}/gym`

### Response
#### 204
```
  No content
```
#### 200
```Json
{
    "id": "3f9bd5b3",
    "name": "Another Gym",
    "logo": "https://via.placeholder.com/700x500",
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

## Set user's preferred gym

`POST` `/api/users/{id}/gym/add`

### Request

```Json
{
	"gymId":"d9806f62"
}
```

### Response

```Json
{
    "message": "Gym added"
}
```

## Remove user's preferred gym

`POST` `/api/users/{id}/gym/remove`

### Response

```Json
{
    "message": "Gym removed"
}
```

## Create user

`GET` `/api/users/create`

### Request

```Json
{
    "id": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya",
    "weight": 65,
    "targetWeight": 60
}
```

### Response
#### 201
```Json
{
    "id": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya",
    "weight": 65,
    "targetWeight": 60
}
```
#### 409
```Json
{
    "message": "id oPlUSF8I0UOqUDsUhy80kMj59SV2 already exists"
}
```
## Update user

`POST` `/api/users/update`

### Request

```Json
{
    "id": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya",
    "weight": 65,
    "targetWeight": 60
}
```

### Response
#### 200
```Json
{
    "id": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya",
    "weight": 65,
    "targetWeight": 60
}
```
