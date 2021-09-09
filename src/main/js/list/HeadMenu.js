import React, {useState, useEffect} from 'react';
import IconButton from '@material-ui/core/IconButton';
import Menu from '@material-ui/core/Menu';
import MenuItem from '@material-ui/core/MenuItem';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import {ClickAwayListener, Grow, MenuList, Paper, Popper} from "@material-ui/core";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
    root: {
        display: 'flex',
    },
    paper: {
        marginRight: theme.spacing(2),
    },
}));

export default function HeadMenu(props) {
    const { colId, columnVisibility, setColumnVisibility, handleColMenuToggle } = props;
    const classes = useStyles();
    const [open, setOpen] = useState(false);
    const anchorRef = React.useRef(null);

    const handleToggle = () => {
        setOpen((prevOpen) => !prevOpen);
    };

    const handleClose = (event) => {
        if (anchorRef.current && anchorRef.current.contains(event.target)) {
            return;
        }

        setOpen(false);
    };

    const handleHideCol = (event) => {
        setColumnVisibility({
            ...columnVisibility,
            [colId]: false,
        });
    };

    function handleListKeyDown(event) {
        if (event.key === 'Tab') {
            event.preventDefault();
            setOpen(false);
        }
    }

    // return focus to the button when we transitioned from !open -> open
    const prevOpen = React.useRef(open);
    useEffect(() => {
        if (prevOpen.current === true && open === false) {
            anchorRef.current.focus();
        }

        prevOpen.current = open;
    }, [open]);

    return (
        <div className={classes.root}>
            <IconButton
                size={'small'}
                aria-label="more"
                aria-controls={open ? 'menu-list-grow' : undefined}
                aria-haspopup="true"

                ref={anchorRef}
                onClick={handleToggle}
            >
                <MoreVertIcon />
            </IconButton>
            <Popper open={open} anchorEl={anchorRef.current} role={undefined} transition disablePortal>
                {({ TransitionProps, placement }) => (
                    <Grow
                        {...TransitionProps}
                        style={{ transformOrigin: placement === 'bottom' ? 'center top' : 'center bottom' }}
                    >
                        <Paper>
                            <ClickAwayListener onClickAway={handleClose}>
                                <MenuList autoFocusItem={open} id="menu-list-grow" onKeyDown={handleListKeyDown}>
                                    <MenuItem onClick={handleHideCol}>Hide</MenuItem>
                                    <MenuItem onClick={e => { handleClose(e);  handleColMenuToggle() }}>
                                        Show Columns
                                    </MenuItem>
                                </MenuList>
                            </ClickAwayListener>
                        </Paper>
                    </Grow>
                )}
            </Popper>
        </div>
    );
}

// const options = [
//     'None',
//     'Atria',
//     'Callisto',
//     'Dione',
//     'Ganymede',
//     'Hangouts Call',
//     'Luna',
//     'Oberon',
//     'Phobos',
//     'Pyxis',
//     'Sedna',
//     'Titania',
//     'Triton',
//     'Umbriel',
// ];
//
// const ITEM_HEIGHT = 48;
//
// export default function HeadMenu() {
//     const [anchorEl, setAnchorEl] = React.useState(null);
//     const open = Boolean(anchorEl);
//
//     const handleClick = (event) => {
//         setAnchorEl(event.currentTarget);
//     };
//
//     const handleClose = () => {
//         setAnchorEl(null);
//     };
//
//     return (
//         <div>
//             <IconButton
//                 size={'small'}
//                 aria-label="more"
//                 aria-controls="long-menu"
//                 aria-haspopup="true"
//                 onClick={handleClick}
//             >
//                 <MoreVertIcon />
//             </IconButton>
//             <Menu
//                 id="long-menu"
//                 anchorEl={anchorEl}
//                 keepMounted
//                 open={open}
//                 onClose={handleClose}
//                 PaperProps={{
//                     style: {
//                         maxHeight: ITEM_HEIGHT * 4.5,
//                         width: '20ch',
//                     },
//                 }}
//             >
//                 {options.map((option) => (
//                     <MenuItem key={option} selected={option === 'Pyxis'} onClick={handleClose}>
//                         {option}
//                     </MenuItem>
//                 ))}
//             </Menu>
//         </div>
//     );
// }
