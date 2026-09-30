export function nextUUId() {

    if (crypto?.randomUUID) {
        return crypto.randomUUID();
    }

    return ++(nextUUId._currentId);
}

nextUUId._currentId = 0;