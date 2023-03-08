import { e } from "../common.js";

const NameTitle = (props) => {
    const { mutable, username, setusername, randomName } = props;
    return e('h1', {},
        e('button', {
            disabled: !mutable,
            onClick: randomName
        }, '🔀'),
        e('button', { disabled: !mutable, onClick: () => { setusername(prompt("name:", username)) } }, '🖋️'),
        ' ',
        username
    );
}

export { NameTitle };