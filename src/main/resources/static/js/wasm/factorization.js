let wasm_bindgen;
(function () {
    const __exports = {};
    let wasm;

    let WASM_VECTOR_LEN = 0;

    let cachegetUint8Memory0 = null;

    function getUint8Memory0() {
        if (cachegetUint8Memory0 === null || cachegetUint8Memory0.buffer !== wasm.memory.buffer) {
            cachegetUint8Memory0 = new Uint8Array(wasm.memory.buffer);
        }
        return cachegetUint8Memory0;
    }

    let cachedTextEncoder = new TextEncoder('utf-8');

    const encodeString = (typeof cachedTextEncoder.encodeInto === 'function'
        ? function (arg, view) {
            return cachedTextEncoder.encodeInto(arg, view);
        }
        : function (arg, view) {
            const buf = cachedTextEncoder.encode(arg);
            view.set(buf);
            return {
                read: arg.length,
                written: buf.length
            };
        });

    function passStringToWasm0(arg, malloc, realloc) {

        if (realloc === undefined) {
            const buf = cachedTextEncoder.encode(arg);
            const ptr = malloc(buf.length);
            getUint8Memory0().subarray(ptr, ptr + buf.length).set(buf);
            WASM_VECTOR_LEN = buf.length;
            return ptr;
        }

        let len = arg.length;
        let ptr = malloc(len);

        const mem = getUint8Memory0();

        let offset = 0;

        for (; offset < len; offset++) {
            const code = arg.charCodeAt(offset);
            if (code > 0x7F) break;
            mem[ptr + offset] = code;
        }

        if (offset !== len) {
            if (offset !== 0) {
                arg = arg.slice(offset);
            }
            ptr = realloc(ptr, len, len = offset + arg.length * 3);
            const view = getUint8Memory0().subarray(ptr + offset, ptr + len);
            const ret = encodeString(arg, view);

            offset += ret.written;
        }

        WASM_VECTOR_LEN = offset;
        return ptr;
    }

    let cachegetInt32Memory0 = null;

    function getInt32Memory0() {
        if (cachegetInt32Memory0 === null || cachegetInt32Memory0.buffer !== wasm.memory.buffer) {
            cachegetInt32Memory0 = new Int32Array(wasm.memory.buffer);
        }
        return cachegetInt32Memory0;
    }

    let cachedTextDecoder = new TextDecoder('utf-8', {ignoreBOM: true, fatal: true});

    cachedTextDecoder.decode();

    function getStringFromWasm0(ptr, len) {
        return cachedTextDecoder.decode(getUint8Memory0().subarray(ptr, ptr + len));
    }

    /**
     * @param {number} smooth
     * @param {string} base
     * @param {string} data
     * @returns {string}
     */
    __exports.compute_linear_equations = function (smooth, base, data) {
        try {
            const retptr = wasm.__wbindgen_export_0.value - 16;
            wasm.__wbindgen_export_0.value = retptr;
            var ptr0 = passStringToWasm0(base, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
            var len0 = WASM_VECTOR_LEN;
            var ptr1 = passStringToWasm0(data, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
            var len1 = WASM_VECTOR_LEN;
            wasm.compute_linear_equations(retptr, smooth, ptr0, len0, ptr1, len1);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            return getStringFromWasm0(r0, r1);
        } finally {
            wasm.__wbindgen_export_0.value += 16;
            wasm.__wbindgen_free(r0, r1);
        }
    };

    /**
     * @param {number} smooth
     * @param {string} from
     * @param {string} to
     * @param {string} base
     * @returns {string}
     */
    __exports.compute_perfect_squares = function (smooth, from, to, base) {
        try {
            const retptr = wasm.__wbindgen_export_0.value - 16;
            wasm.__wbindgen_export_0.value = retptr;
            var ptr0 = passStringToWasm0(from, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
            var len0 = WASM_VECTOR_LEN;
            var ptr1 = passStringToWasm0(to, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
            var len1 = WASM_VECTOR_LEN;
            var ptr2 = passStringToWasm0(base, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
            var len2 = WASM_VECTOR_LEN;
            wasm.compute_perfect_squares(retptr, smooth, ptr0, len0, ptr1, len1, ptr2, len2);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            return getStringFromWasm0(r0, r1);
        } finally {
            wasm.__wbindgen_export_0.value += 16;
            wasm.__wbindgen_free(r0, r1);
        }
    };

    let cachegetUint32Memory0 = null;

    function getUint32Memory0() {
        if (cachegetUint32Memory0 === null || cachegetUint32Memory0.buffer !== wasm.memory.buffer) {
            cachegetUint32Memory0 = new Uint32Array(wasm.memory.buffer);
        }
        return cachegetUint32Memory0;
    }

    function getArrayU32FromWasm0(ptr, len) {
        return getUint32Memory0().subarray(ptr / 4, ptr / 4 + len);
    }

    /**
     * @param {number} limit
     * @returns {Uint32Array}
     */
    __exports.list_of_primes = function (limit) {
        try {
            const retptr = wasm.__wbindgen_export_0.value - 16;
            wasm.__wbindgen_export_0.value = retptr;
            wasm.list_of_primes(retptr, limit);
            var r0 = getInt32Memory0()[retptr / 4 + 0];
            var r1 = getInt32Memory0()[retptr / 4 + 1];
            var v0 = getArrayU32FromWasm0(r0, r1).slice();
            wasm.__wbindgen_free(r0, r1 * 4);
            return v0;
        } finally {
            wasm.__wbindgen_export_0.value += 16;
        }
    };

    /**
     * @param {string} a
     */
    __exports.greet = function (a) {
        var ptr0 = passStringToWasm0(a, wasm.__wbindgen_malloc, wasm.__wbindgen_realloc);
        var len0 = WASM_VECTOR_LEN;
        wasm.greet(ptr0, len0);
    };

    async function load(module, imports) {
        if (typeof Response === 'function' && module instanceof Response) {

            if (typeof WebAssembly.instantiateStreaming === 'function') {
                try {
                    return await WebAssembly.instantiateStreaming(module, imports);

                } catch (e) {
                    if (module.headers.get('Content-Type') != 'application/wasm') {
                        console.warn("`WebAssembly.instantiateStreaming` failed because your server does not serve wasm with `application/wasm` MIME type. Falling back to `WebAssembly.instantiate` which is slower. Original error:\n", e);

                    } else {
                        throw e;
                    }
                }
            }

            const bytes = await module.arrayBuffer();
            return await WebAssembly.instantiate(bytes, imports);

        } else {

            const instance = await WebAssembly.instantiate(module, imports);

            if (instance instanceof WebAssembly.Instance) {
                return {instance, module};

            } else {
                return instance;
            }
        }
    }

    async function init(input) {
        if (typeof input === 'undefined') {
            let src;
            if (typeof document === 'undefined') {
                src = location.href;
            } else {
                src = document.currentScript.src;
            }
            input = src.replace(/\.js$/, '_bg.wasm');
        }
        const imports = {};
        imports.wbg = {};
        imports.wbg.__wbg_alert_6b7ceba244f51614 = function (arg0, arg1) {
            alert(getStringFromWasm0(arg0, arg1));
        };

        if (typeof input === 'string' || (typeof Request === 'function' && input instanceof Request) || (typeof URL === 'function' && input instanceof URL)) {
            input = fetch(input);
        }

        const {instance, module} = await load(await input, imports);

        wasm = instance.exports;
        init.__wbindgen_wasm_module = module;

        return wasm;
    }

    wasm_bindgen = Object.assign(init, __exports);

})();

const {greet, list_of_primes, compute_perfect_squares, compute_linear_equations} = wasm_bindgen;

async function run() {
    await wasm_bindgen("factorization_bg.wasm");
    //greet("hey");
    //smooth from to to_factor
    console.log(compute_perfect_squares(17, "735", "900", "539873"));
    data = "735;352;1 0 0 0 1 0 0$" +
        "750;22627;0 0 0 0 1 0 1$" +
        "783;73216;1 0 0 0 1 1 0$" +
        "801;101728;1 0 0 0 1 0 0";
    console.log(compute_linear_equations(17, "539873", data));
    console.log(list_of_primes(17));
    setTimeout(work, 500);
}

run();

async function work() {
    await handelTask();
    setTimeout(work, 500);
}

async function handelTask() {
    return new Promise(function (resolve, reject) {
        fetch('/task', {
            method: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
        }).then(async res => {
            let data = await res.json();
            console.log(data);
            if (data.type === "SOLVE_LINEAR") {
                processPerfectSquare(data);
                resolve("SOLVE_LINEAR");
            } else if (data.type === "PAIRS") {
                processPerfectSquare(data);
                resolve("PAIRS");
            } else {
                reject("WRONG TYPE")
            }
        });
    });
}

function processLinearEquations(data) {
    let processed = "";
    for (const number of data.factorizedNumbers) {
        processed += number.num + ";" + number.num2 + ";";
        for (const [prime, p] of Object.entries(number.factors)) {
            processed += p + " ";
        }
        processed += "$"
    }
    let res = compute_linear_equations(Number(data.b), data.n, processed);
    console.log(res);
    let primes = res.split(';');
    if (primes.length === 2) {
        fetch('/task', {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                type: "SOLVE_LINEAR",
                n: data.n,
                p: primes[0],
                q: primes[1],
            }),
        }).then(data => {
                console.log(res)
            }
        )
    }
}

function processPerfectSquare(data) {
    let numbers = compute_perfect_squares(Number(data.b), data.rangeMin, data.rangeMax, data.n).split('$');
    let res = [];
    const primes = list_of_primes(data.b);
    for (let n of numbers) {
        if (n.trim() !== "") {
            let [big, small, parity] = n.split(';');
            let map = new Map();
            let par = parity.split(" ");
            for (let i = 0; i < par.length; i++) {
                map.set(primes[i], par[i]);
            }
            res.push({num: big, num2: small, factors: map})
        }
    }
    fetch('/task', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            type: "PAIRS",
            n: data.n,
            factorizedNumbers: res
        }),
    }).then(data => {
        console.log(res)
    });
}
