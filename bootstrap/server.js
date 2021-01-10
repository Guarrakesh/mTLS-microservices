'use strict';

const express = require('express');
const yaml = require('js-yaml');
const fs = require('fs');
const axios = require('axios');


// Constants
const PORT = 8080;
const HOST = '0.0.0.0';

const API_KEY = process.env.API_KEY;
const ENDPOINT_USER_KEY_RAW = process.env.ENDPOINT_USER_KEY_RAW;
const ENDPOINT_USER_KEYS = process.env.ENDPOINT_USER_KEYS;

// App
const app = express();

function bootstrapHandler(req, res) {
    try {
        let fileContents = fs.readFileSync('./microservices.yml', 'utf8');
        let data = yaml.load(fileContents);

        for (const microservice of data.microservices) {
            getLongLivedForMicroservice(microservice);
        }

        res.send('Successfully deployed!');
    } catch (e) {
        console.log(e);
        res.send(e.message);
    }
}

/**
* Retrieve a Long Lived JWT for a Microservice and deliver it to its endpoint.
* @param microservice the microservice to deliver the Credential to.
* @return void
*/
async function getLongLivedForMicroservice(microservice)
{
    const endpoint = ENDPOINT_USER_KEYS.replace(':userId', microservice.userId);
    console.log(`Creating Token for ${microservice.name}`)
        // check if microservice has active Api_keys
    const response = await axios.get(endpoint, {
        headers: { Authorization: `Bearer ${API_KEY}`}
    })
    if (response.data.total === 0) {
        console.log(`[${microservice.name}] No existent JWT for this microservice. Creating and deploying a new one...`);
       return await createJWT(microservice.userId, microservice.name);
    } else {
        for (const item of response.data.items) {
        const expiration = new Date(item.ttl * 1000);
            if (!item.revoked && expiration > (new Date())) {
                console.log(`[${microservice.name}] Found an existent valid JWT, deploying this.`);
                return await getRawJWT(item);
            }
        }
        // All key are revoked or expired, create new one
        console.log(`[${microservice.name}] All existend JWT are revoked/expired, creating and deploying a new one.`);
        return await createJWT(microservice.userId, microservice.name);
    }


}

/**
* Create a JWT for the given user
* @return string the user encoded JWT
*/
async function createJWT(userId, name)
{
    const endpoint = ENDPOINT_USER_KEYS.replace(':userId', userId);
    const now = new Date();
    try {
        const response = await axios.post(endpoint, {
            ttl: now.setFullYear(now.getFullYear() + 1),
            name: `JWT Long Lived - ${name}`
        },
        {
            headers: { Authorization: `Bearer ${API_KEY}`}
        });
        return response.data.jwt;

    } catch (e) {
        console.log(`[${name}] Could not create JWT for ${name}: ${e.message} `)
        return null;
    }
}

/**
* Given a LEMUR API_KEY item, retrieve its encoded JWT
* @return string The encoded JWT
*/
async function getRawJWT(apiKey) {
    const endpoint = ENDPOINT_USER_KEY_RAW.replace(':keyId', apiKey.id);
    const response = await axios.get(endpoint, { headers: { Authorization: `Bearer ${API_KEY}`} });

    return response.data.jwt;
}



app.get('/', (req, res) => {
  res.send('Hello, World!');
});

app.get('/deploy', bootstrapHandler);

app.listen(PORT, HOST);
console.log(`Running on http://${HOST}:${PORT}`);