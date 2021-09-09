import {Divider, Grid, TableHead, TableSortLabel, Typography} from "@material-ui/core";
import TableHeadActions from "./TableHeadActions";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import HeadMenu from "./HeadMenu";
import PropTypes from "prop-types";
import React, {useRef, useState} from "react";
import ColumnMenu from "./ColumnMenu";
import {makeStyles} from "@material-ui/core/styles";

const useStyles = makeStyles((theme) => ({
    headerRow: {
        height: "81px",
        alignContent: "center",
    },
    visuallyHidden: {
        border: 0,
        clip: 'rect(0 0 0 0)',
        height: 1,
        margin: -1,
        overflow: 'hidden',
        padding: 0,
        position: 'absolute',
        top: 20,
        width: 1,
    },
    tableCell: {
        fontSize: '1.2rem',
    },
    id: {
        fontSize: '1.0rem',
        width: "50px",
    },
    pinyinNumbered: {
        fontSize: '1.0rem',
    },
    level: {
        fontSize: '1.0rem',
        width: "100px",
    },
}));

const headCells = [
    { id: 'id', numeric: false, disablePadding: false, label: 'id' },
    { id: 'wordSimplified', numeric: false, disablePadding: false, label: 'Word (Simplified)' },
    { id: 'wordTraditional', numeric: false, disablePadding: false, label: 'Word (Traditional)' },
    { id: 'pinyin', numeric: false, disablePadding: false, label: 'pinyin' },
    { id: 'pinyinNumbered', numeric: false, disablePadding: false, label: 'pinyin (numbered)' },
    { id: 'meaning', numeric: false, disablePadding: false, label: 'Meaning' },
    { id: 'level', numeric: true, disablePadding: false, label: 'Level' },
];

export default function EnhancedTableHead(props) {
    const { order, orderBy, onRequestSort,
        isCumulative, handleCumulativeChange, rowCount,  rowsPerPage, page, handleChangePage, handleChangeRowsPerPage,
        columnVisibility, setColumnVisibility } = props;
    const classes = useStyles();

    const [openColMenu, setOpenColMenu] = useState(false);
    const anchorRef = useRef(null);

    const createSortHandler = (property) => (event) => {
        onRequestSort(event, property);
    };

    const handleColMenuToggle = () => {
        setOpenColMenu((prevOpen) => !prevOpen);
    };

    return (
        <TableHead ref={anchorRef} aria-controls={open ? 'menu-list-grow' : undefined}>
            <TableHeadActions
                isCumulative={isCumulative}
                handleCumulativeChange={handleCumulativeChange}
                rowCount={rowCount}
                rowsPerPage={rowsPerPage}
                page={page}
                handleChangePage={handleChangePage}
                handleChangeRowsPerPage={handleChangeRowsPerPage}
            />
            <TableRow className={classes.headerRow}>
                {headCells.map((headCell) => (
                    columnVisibility[headCell.id] &&
                            <TableCell
                                key={headCell.id}
                                align={headCell.numeric ? 'right' : 'left'}
                                padding={headCell.disablePadding ? 'none' : 'normal'}
                                sortDirection={orderBy === headCell.id ? order : false}
                                className={classes[headCell.id] || classes.tableCell}
                            >
                                <Grid container justifyContent={"space-between"}>
                                    <Grid item xs zeroMinWidth>
                                        <TableSortLabel
                                            active={orderBy === headCell.id}
                                            direction={orderBy === headCell.id ? order : 'asc'}
                                            onClick={createSortHandler(headCell.id)}
                                        >
                                            {headCell.label}
                                            {orderBy === headCell.id ? (
                                                <span className={classes.visuallyHidden}>
                                            {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                                        </span>
                                            ) : null}
                                        </TableSortLabel>
                                    </Grid>
                                    <div style={{"display": "flex"}}>
                                        <HeadMenu colId={headCell.id}
                                                  columnVisibility={columnVisibility}
                                                  setColumnVisibility={setColumnVisibility}
                                                  handleColMenuToggle={handleColMenuToggle}
                                        />
                                        <Divider light orientation="vertical" flexItem/>
                                    </div>
                                </Grid>
                            </TableCell>
                ))}
            </TableRow>
            <ColumnMenu open={openColMenu}
                        setOpen={setOpenColMenu}
                        anchorRef={anchorRef}
                        headCells={headCells}
                        columnVisibility={columnVisibility}
                        setColumnVisibility={setColumnVisibility}
            />
        </TableHead>
    );
}

EnhancedTableHead.propTypes = {
    onRequestSort: PropTypes.func.isRequired,
    order: PropTypes.oneOf(['asc', 'desc']).isRequired,
    orderBy: PropTypes.string.isRequired,
    rowCount: PropTypes.number.isRequired,
};
