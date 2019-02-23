# Sessions

## Get sessions for user

`GET` `/api/sessions/{id}`

id - User's id

### Response
#### 200
```Json
[
    {
        "id": "26a9e6b1",
        "name": "Assassin's",
        "user": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
        "date": 1530951821,
        "gym": "3f9bd5b3",
        "complete": false,
        "steps": [
            {
                "reps": "40",
                "sets": 3,
                "stepIndex": 0,
                "title": "Jumping Ts"
            },
            {
                "reps": "30",
                "sets": 3,
                "stepIndex": 1,
                "title": "Box Jumps"
            }
        ]
    }
]
```

## Create session

`POST` `/api/sessions/create`

### Request
```Json
{
    "complete": false,
    "user": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "name": "Assassin's",
    "date": 1530951821,
    "gym": "3f9bd5b3",
    "steps": [
        {
            "reps": "40",
            "sets": 3,
            "stepIndex": 0,
            "title": "Jumping Ts"
        },
        {
            "reps": "30",
            "sets": 3,
            "stepIndex": 1,
            "title": "Box Jumps"
        }
    ]
}
```

### Response
#### 201
```Json
{
    "id": "26a9e6b1",
    "name": "Assassin's",
    "user": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "date": 1530951821,
    "gym": "3f9bd5b3",
    "complete": false,
    "steps": [
        {
            "reps": "40",
            "sets": 3,
            "stepIndex": 0,
            "title": "Jumping Ts"
        },
        {
            "reps": "30",
            "sets": 3,
            "stepIndex": 1,
            "title": "Box Jumps"
        }
    ]
}
```

## Update session

`POST` `/api/sessions/update`

### Request

```Json
{
    "id": "26a9e6b1",
    "name": "Assassin's",
    "user": "oPlUSF8I0UOqUDsUhy80kMj59SV2",
    "date": 1530951821,
    "gym": "3f9bd5b3",
    "complete": true,
    "steps": [
        {
            "reps": "40",
            "sets": 3,
            "stepIndex": 0,
            "title": "Jumping Ts"
        },
        {
            "reps": "30",
            "sets": 3,
            "stepIndex": 1,
            "title": "Box Jumps"
        }
    ]
}
```

### Response
#### 200
```Json
{
    "message": "Session updated"
}
```

## Delete session

`DELETE` `/api/sessions/delete/{id}`

id - Session id

### Response

```Json
{
    "message": "Session deleted"
}
```