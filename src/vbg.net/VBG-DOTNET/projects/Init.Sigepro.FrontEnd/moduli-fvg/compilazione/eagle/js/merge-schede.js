export function mergeSchede(listaSchede) {
    const merged = {};

    for (const scheda of listaSchede) {

        if (merged[scheda.layerName] == undefined) {
            merged[scheda.layerName] = scheda;
            continue;
        }

        merged[scheda.layerName].mergeValues(scheda);
    }

    const mergedSet = [];

    for (const key in merged) {
        if (Object.hasOwnProperty.call(merged, key)) {
            mergedSet.push(merged[key]);
        }
    }

    return mergedSet;
}