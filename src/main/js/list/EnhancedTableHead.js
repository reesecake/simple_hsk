import {Divider, Grid, TableHead, TableSortLabel} from "@material-ui/core";
import TableHeadActions from "./TableHeadActions";
import TableRow from "@material-ui/core/TableRow";
import TableCell from "@material-ui/core/TableCell";
import HeadMenu from "./HeadMenu";
import PropTypes from "prop-types";
import React from "react";

const headCells = [
    { id: 'wordSimplified', numeric: false, disablePadding: false, label: 'Word (Simplified)' },
    { id: 'pinyin', numeric: false, disablePadding: false, label: 'pinyin' },
    { id: 'meaning', numeric: false, disablePadding: false, label: 'Meaning' },
    { id: 'level', numeric: true, disablePadding: false, label: 'Level' },
];

export default function EnhancedTableHead(props) {
    const { classes, order, orderBy, onRequestSort, isCumulative, handleCumulativeChange, rowCount,  rowsPerPage, page, handleChangePage, handleChangeRowsPerPage } = props;
    const createSortHandler = (property) => (event) => {
        onRequestSort(event, property);
    };

    return (
        <TableHead>
            <TableHeadActions
                isCumulative={isCumulative}
                handleCumulativeChange={handleCumulativeChange}
                rowCount={rowCount}
                rowsPerPage={rowsPerPage}
                page={page}
                handleChangePage={handleChangePage}
                handleChangeRowsPerPage={handleChangeRowsPerPage}
            />
            <TableRow>
                {headCells.map((headCell) => (
                    <TableCell
                        key={headCell.id}
                        align={headCell.numeric ? 'right' : 'left'}
                        padding={headCell.disablePadding ? 'none' : 'normal'}
                        sortDirection={orderBy === headCell.id ? order : false}
                        className={classes.tableCell}
                    >
                        <Grid container justifyContent={"space-between"}>
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
                            <div style={{"display": "flex"}}>
                                <HeadMenu />
                                <Divider orientation="vertical" flexItem />
                            </div>
                        </Grid>
                    </TableCell>
                ))}
            </TableRow>
        </TableHead>
    );
}

EnhancedTableHead.propTypes = {
    classes: PropTypes.object.isRequired,
    onRequestSort: PropTypes.func.isRequired,
    order: PropTypes.oneOf(['asc', 'desc']).isRequired,
    orderBy: PropTypes.string.isRequired,
    rowCount: PropTypes.number.isRequired,
};
