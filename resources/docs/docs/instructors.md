# Instructors

## Get all instructors

`GET` `/api/instructors`

### Response
#### 200
```Json
[
    {
        "id": "e9a7bb8b",
        "firstName": "fName",
        "lastName": "lName",
        "email": "fName@mail.com",
        "photo": "https://via.placeholder.com/500x500",
        "yearOfBirth": 1998,
        "gender": "M",
        "country": "Kenya"
    }
]
```

## Get instructor by id

`GET` `/api/instructors/{id}`

### Response
#### 200
```Json
{
    "id": "e9a7bb8b",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya"
}
```
#### 404
```Json
{
    "message": "There is no instructor with id e9a7bb8bs"
}
```
## Get instructor's gym

`GET` `/api/instructors/{id}/gym`

### Response
#### 204
```
No Content
```
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

## Set instructor's gym

`POST` `/api/instructors/{id}/gym/add`

### Request

```Json
{
	"gymId":"e64cd665"
}
```

### Response
#### 200
```Json
{
    "message": "Gym added"
}
```

## Remove instructor's gym

`POST` `/api/instructors/{id}/gym/remove`

### Response
#### 200
```Json
{
    "message": "Gym removed"
}
```

## Create instructor

`POST` `/api/instructors/create`

### Request

```Json
{
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya"
  }
```

### Response
#### 201
```Json
{
    "id": "e9a7bb8b",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya"
}
```
#### 409
```Json
{
    "message": "email fName@mail.com already exists"
}
```
## Update instructor

`POST` `/api/instructors/update`

### Request

```Json
{
    "id": "e9a7bb8b",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya"
}
```

### Response
#### 200
```Json
{
    "id": "e9a7bb8b",
    "firstName": "fName",
    "lastName": "lName",
    "email": "fName@mail.com",
    "photo": "https://via.placeholder.com/500x500",
    "yearOfBirth": 1998,
    "gender": "M",
    "country": "Kenya"
}
```
