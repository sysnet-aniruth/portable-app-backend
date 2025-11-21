const fs = require("fs");
const crypto = require("crypto");

const privateKey = fs.readFileSync("private_key.pem", "utf8");

// --------- INSERT THESE TWO VALUES ----------
const hardwareId = "93489bc0c3c73776ed70562d4b775060a8ef29452140571ee5a05620fed25bbb";
const companyId = "SCHIMA-SYSTEMS";
// --------------------------------------------

const licenseData = {
  fingerprint: hardwareId,
  company_id: companyId,
  issued_on: new Date().toISOString(),
  expiry: "2030-01-01" // optional
};

const json = JSON.stringify(licenseData);

// Encrypt with PRIVATE KEY
const encrypted = crypto.privateEncrypt(privateKey, Buffer.from(json));

// Save to license file
fs.writeFileSync("license.enc", encrypted);

console.log("✔ license.enc generated!");
