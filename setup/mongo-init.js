db.createUser(
        {
            user: "probe-user",
            pwd: "pr0be_Ch@lleng&2o24!",
            roles: [
                {
                    role: "readWrite",
                    db: "db_probe-challenge"
                }
            ]
        }
);