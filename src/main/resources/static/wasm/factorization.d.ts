/* tslint:disable */

/* eslint-disable */
/**
 * @param {number} smooth
 * @param {string} base
 * @param {string} data
 * @returns {string}
 */
export function compute_linear_equations(smooth: number, base: string, data: string): string;

/**
 * @param {number} smooth
 * @param {string} from
 * @param {string} to
 * @param {string} base
 * @returns {string}
 */
export function compute_perfect_squares(smooth: number, from: string, to: string, base: string): string;

/**
 * @param {string} a
 */
export function greet(a: string): void;

export type InitInput = RequestInfo | URL | Response | BufferSource | WebAssembly.Module;

export interface InitOutput {
    readonly memory: WebAssembly.Memory;
    readonly compute_linear_equations: (a: number, b: number, c: number, d: number, e: number, f: number) => void;
    readonly compute_perfect_squares: (a: number, b: number, c: number, d: number, e: number, f: number, g: number, h: number) => void;
    readonly greet: (a: number, b: number) => void;
    readonly __wbindgen_malloc: (a: number) => number;
    readonly __wbindgen_realloc: (a: number, b: number, c: number) => number;
    readonly __wbindgen_free: (a: number, b: number) => void;
}

/**
 * If `module_or_path` is {RequestInfo} or {URL}, makes a request and
 * for everything else, calls `WebAssembly.instantiate` directly.
 *
 * @param {InitInput | Promise<InitInput>} module_or_path
 *
 * @returns {Promise<InitOutput>}
 */
export default function init(module_or_path?: InitInput | Promise<InitInput>): Promise<InitOutput>;
        