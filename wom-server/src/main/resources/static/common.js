// restful api start =========
const whenErr = error => console.error(error);
const checkError = response => {
    if (!response.ok) {
        throw new Error(response.json().errorMessage);
    }
    return response;
};

const covertJson = response => response.json();
const covertText = response => response.text();
const parseJson = text => JSON.parse(text);

// catch 加载前面会往下游传 null
// 'http://localhost:8081' + 
const rest = {
    get: url => fetch(url).then(checkError).catch(whenErr),
    delete: url => fetch(url, { method: 'DELETE' }).then(checkError).catch(whenErr),
    post: (url, obj) => fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(obj),
    }).then(checkError).catch(whenErr),
    put: (url, obj) => fetch(url, {
        method: 'PUT',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(obj),
    }).then(checkError).catch(whenErr),
}
// restful api end =========

// react api start =========
const e = React.createElement;
const rState = React.useState;
const rEffect = React.useEffect;
// react api end =========

// mine api start =========
const localName = newName => newName ? localStorage.setItem('username', newName) : localStorage.getItem('username');
const localClear = () => localStorage.clear();
// mine api start =========

export {
    covertJson, covertText, parseJson, rest,
    e, rState, rEffect,
    localName, localClear
};