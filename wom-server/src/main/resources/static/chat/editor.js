import { e, rState } from "../common.js";

const Editor = (props) => {
    const { sendMessage } = props;
    const [input, setinput] = rState('');
    return e('form',
        { onSubmit: e => { e.preventDefault(); sendMessage(input); setinput(''); } },
        e('input', {
            type: 'text',
            placeholder: 'write something...',
            value: input,
            onChange: e => setinput(e.target.value),
        }));
}

export { Editor };