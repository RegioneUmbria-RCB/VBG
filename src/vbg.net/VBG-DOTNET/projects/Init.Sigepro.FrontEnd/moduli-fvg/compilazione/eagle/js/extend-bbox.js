export function extendBBox(bboxes) {

    const bounds = new L.LatLngBounds(
        [[bboxes[0].bottom, bboxes[0].right], [bboxes[0].top, bboxes[0].left]]
    );


    for (let i = 1; i < bboxes.length; i++) {
        const l = new L.LatLngBounds(
            [[bboxes[i].bottom, bboxes[i].right], [bboxes[i].top, bboxes[i].left]]
        )

        bounds.extend(l);
    }

    return bounds;
}