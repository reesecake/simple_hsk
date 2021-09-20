import TableRow from "@material-ui/core/TableRow";
import {Checkbox, FormControlLabel} from "@material-ui/core";
import TablePagination from "@material-ui/core/TablePagination";
import TablePaginationActions from "./TablePaginationActions";
import React from "react";

export default function TableHeadActions(props) {
    const { isCumulative, handleCumulativeChange, rowCount, rowsPerPage, page, handleChangePage, handleChangeRowsPerPage, columnVisibility } = props;

    return (
        <TableRow>
            <th className={"MuiTableCell-root MuiTableCell-head MuiTablePagination-root"}
                style={{borderBottom: "1px solid rgba(224, 224, 224, 1)"}}
            >
                <FormControlLabel
                    style={{marginLeft: '0px', marginBottom: '0px', }}
                    control={<Checkbox checked={isCumulative} onChange={handleCumulativeChange} name="checkedCumu" />}
                    label="Cumulative"
                />
            </th>

            <TablePagination
                rowsPerPageOptions={[10, 25, 50, 100, 500, 1000, { label: 'All', value: rowCount }]}
                colSpan={Object.values(columnVisibility).reduce((a, item) => a + item, 0)}
                count={rowCount}
                rowsPerPage={rowsPerPage}
                page={page}
                SelectProps={{
                    inputProps: { 'aria-label': 'rows per page' },
                    native: true,
                }}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
                ActionsComponent={TablePaginationActions}
            />
        </TableRow>
    );
}
