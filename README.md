xView detections DFDL Schema
===

https://daffodil.apache.org/

The xView detector outputs detections as a set of space separated values

```
376 180 393 207 4 0.9652593
137 200 160 235 4 0.012583941
356 390 388 414 4 0.006899923
0 0 530 209 4 0.006551653
0 4 342 519 4 0.005900055
350 387 379 413 4 0.005890399
111 24 413 502 4 0.0053548813
42 0 477 290 4 0.0048193634
...
```

This example app will process those into

```
{
  "file": {
    "record": [
      {
        "p0": [
          "376",
          "180"
        ],
        "p1": [
          "393",
          "207"
        ],
        "class": "4",
        "confidence": "0.9652593"
      },
      {
        "p0": [
          "137",
          "200"
        ],
        "p1": [
          "160",
          "235"
        ],
        "class": "4",
        "confidence": "0.012583941"
      },
...
```
