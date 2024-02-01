set -e

mongo <<EOF
db = db.getSiblingDB('seven-challenge')

db.createUser({
  user: 'sevenmars-usr',
  pwd: 'pr0be_Ch@lleng&2o24!',
  roles: [{ role: 'readWrite', db: 'seven-challenge' }],
});
db.createCollection('Planet')

EOF