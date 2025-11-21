const fs = require("fs");
const path = require("path");
const crypto = require("crypto");

// Load private key
const privateKey = fs.readFileSync("keys/private_key.pem", "utf8");

// License data
const hardwareId = "93489bc0c3c73776ed70562d4b775060a8ef29452140571ee5a05620fed25bbb";
const companyId = "SCHIMA-SYSTEMS";

const licenseData = {
  fingerprint: hardwareId,
  company_id: companyId,
  issued_on: new Date().toISOString(),
  expiry: "2030-01-01"
};

const json = JSON.stringify(licenseData);

// Sign the license with SHA256withRSA
const sign = crypto.createSign("SHA256");
sign.update(json);
sign.end();
const signature = sign.sign(privateKey, "base64");

// Ensure backend licenses folder exists
const licensesDir = path.join("..", "portable-backend", "licenses");
if (!fs.existsSync(licensesDir)) fs.mkdirSync(licensesDir, { recursive: true });

// Save license.json in backend folder
const signedLicense = {
    license: licenseData,
    signature: signature
};

fs.writeFileSync(path.join(licensesDir, "license.json"), JSON.stringify(signedLicense, null, 4));
console.log("✔ license.json generated with digital signature!");
